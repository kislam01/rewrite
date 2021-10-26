/*
 * Copyright 2021 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openrewrite.java;

import org.openrewrite.ExecutionContext;
import org.openrewrite.java.search.FindMissingTypes;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.JavaSourceFile;

/**
 * Puts a search result marker on a JavaSourceFile if there is no missing type information according to FindMissingTypes.
 * So when there _are_ missing types, no changes are made.
 *
 * The intended purpose is as a singleSourceApplicabilityTest() for recipes in danger of removing things they should not
 * when type information is missing.
 */
public class NoMissingTypes extends JavaVisitor<ExecutionContext> {

    @Override
    public J visitJavaSourceFile(JavaSourceFile cu, ExecutionContext context) {
        JavaSourceFile cu2 = (JavaSourceFile) new FindMissingTypes().getVisitor().visit(cu, context);
        if(cu2 == cu) {
            return cu.withMarkers(cu.getMarkers().searchResult("All AST elements have type information"));
        }
        return cu;
    }
}
