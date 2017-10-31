/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package capital.scalable.restdocs.javadoc;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;

public class JavadocReaderImplTest {

    private static final String SOURCE_DIR = JavadocReaderImplTest.class.
            getClassLoader().getResource("json").getPath();

    @Test
    public void resolveFieldComment() {
        JavadocReader javadocReader = JavadocReaderImpl.createWith(SOURCE_DIR);
        String comment = javadocReader.resolveFieldComment(SimpleType.class, "simpleField");
        assertThat(comment, equalTo("Simple field comment"));
    }

    @Test
    public void resolveFieldCommentFromClasspath() {
        JavadocReader javadocReader = JavadocReaderImpl.createWith(null);
        String comment = javadocReader.resolveFieldComment(SimpleType.class, "simpleField");
        assertThat(comment, equalTo("Simple field comment from classpath"));
    }

    @Test
    public void resolveFieldCommentAcrossDirectories() {
        String nonExistentDir = new File(SOURCE_DIR, "does-not-exist").getPath();
        String javadocDirs = " ," + nonExistentDir + " , " + SOURCE_DIR;
        JavadocReader javadocReader = JavadocReaderImpl.createWith(javadocDirs);
        String comment = javadocReader.resolveFieldComment(SimpleType.class, "simpleField");
        assertThat(comment, equalTo("Simple field comment"));
    }

    @Test
    public void resolveFieldTag() {
        JavadocReader javadocReader = JavadocReaderImpl.createWith(SOURCE_DIR);
        String comment = javadocReader.resolveFieldTag(SimpleType.class, "simpleField",
                "deprecated");
        assertThat(comment, equalTo("Deprecation comment"));
    }

    @Test
    public void resolveFieldTagFromClasspath() {
        JavadocReader javadocReader = JavadocReaderImpl.createWith(null);
        String comment = javadocReader.resolveFieldTag(SimpleType.class, "simpleField",
                "deprecated");
        assertThat(comment, equalTo("Deprecation comment from classpath"));
    }

    @Test
    public void resolveMethodComment() {
        JavadocReader javadocReader = JavadocReaderImpl.createWith(SOURCE_DIR);
        String comment = javadocReader.resolveMethodComment(SimpleType.class, "simpleMethod");
        assertThat(comment, equalTo("Simple method comment"));
    }

    @Test
    public void resolveMethodCommentFromClasspath() {
        JavadocReader javadocReader = JavadocReaderImpl.createWith(null);
        String comment = javadocReader.resolveMethodComment(SimpleType.class, "simpleMethod");
        assertThat(comment, equalTo("Simple method comment from classpath"));
    }

    @Test
    public void resolveMethodTag() {
        JavadocReader javadocReader = JavadocReaderImpl.createWith(SOURCE_DIR);
        String comment = javadocReader.resolveMethodTag(SimpleType.class, "simpleMethod", "title");
        assertThat(comment, equalTo("Simple method title"));
    }

    @Test
    public void resolveMethodTagFromClasspath() {
        JavadocReader javadocReader = JavadocReaderImpl.createWith(null);
        String comment = javadocReader.resolveMethodTag(SimpleType.class, "simpleMethod", "title");
        assertThat(comment, equalTo("Simple method title from classpath"));
    }

    @Test
    public void resolveMethodParameterComment() {
        JavadocReader javadocReader = JavadocReaderImpl.createWith(SOURCE_DIR);
        String comment = javadocReader.resolveMethodParameterComment(SimpleType.class,
                "simpleMethod", "simpleParameter");
        assertThat(comment, equalTo("Simple parameter comment"));
    }

    @Test
    public void resolveMethodParameterCommentFromClasspath() {
        JavadocReader javadocReader = JavadocReaderImpl.createWith(null);
        String comment = javadocReader.resolveMethodParameterComment(SimpleType.class,
                "simpleMethod", "simpleParameter");
        assertThat(comment, equalTo("Simple parameter comment from classpath"));
    }

    @Test
    public void jsonFileDoesNotExist() {
        JavadocReader javadocReader = JavadocReaderImpl.createWith(SOURCE_DIR);

        // json file for class does not exist
        String comment = javadocReader.resolveFieldComment(NotExisting.class, "simpleField2");
        assertThat(comment, is(""));
        comment = javadocReader.resolveFieldTag(NotExisting.class, "simpleField", "tag");
        assertThat(comment, is(""));
        comment = javadocReader.resolveFieldTag(NotExisting.class, "simpleField2", "tag");
        assertThat(comment, is(""));
        comment = javadocReader.resolveMethodComment(NotExisting.class, "simpleMethod");
        assertThat(comment, is(""));
        comment = javadocReader.resolveMethodTag(NotExisting.class, "simpleMethod", "tag");
        assertThat(comment, is(""));
        comment = javadocReader.resolveMethodTag(NotExisting.class, "simpleMethod2", "tag");
        assertThat(comment, is(""));
        comment = javadocReader.resolveMethodParameterComment(NotExisting.class, "simpleMethod",
                "simpleParameter");
        assertThat(comment, is(""));
    }

    @Test
    public void jsonNotDocumented() {
        JavadocReader javadocReader = JavadocReaderImpl.createWith(SOURCE_DIR);

        // json file exists but field/method/parameter is not present
        String comment = javadocReader.resolveFieldComment(SimpleType.class, "simpleField2");
        assertThat(comment, is(""));
        comment = javadocReader.resolveFieldTag(SimpleType.class, "simpleField", "tag");
        assertThat(comment, is(""));
        comment = javadocReader.resolveFieldTag(SimpleType.class, "simpleField2", "tag");
        assertThat(comment, is(""));
        comment = javadocReader.resolveMethodComment(SimpleType.class, "simpleMethod2");
        assertThat(comment, is(""));
        comment = javadocReader.resolveMethodTag(SimpleType.class, "simpleMethod", "tag");
        assertThat(comment, is(""));
        comment = javadocReader.resolveMethodTag(SimpleType.class, "simpleMethod2", "tag");
        assertThat(comment, is(""));
        comment = javadocReader.resolveMethodParameterComment(SimpleType.class, "simpleMethod",
                "simpleParameter2");
        assertThat(comment, is(""));
        comment = javadocReader.resolveMethodParameterComment(SimpleType.class, "simpleMethod2",
                "simpleParameter2");
        assertThat(comment, is(""));
    }

    // json file in src/test/resources/json
    private static class SimpleType {
        private String simpleField;

        private void simpleMethod(String simpleParameter) {
        }
    }

    private static class NotExisting {
    }
}
