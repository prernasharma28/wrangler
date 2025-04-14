/*
 * Copyright © 2021 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.cdap.directives.transformation;

import io.cdap.cdap.api.annotation.Description;
import io.cdap.cdap.api.annotation.Name;
import io.cdap.cdap.api.annotation.Plugin;
import io.cdap.wrangler.api.Arguments;
import io.cdap.wrangler.api.Directive;
import io.cdap.wrangler.api.ErrorRowException;
import io.cdap.wrangler.api.ExecutorContext;
import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.api.parser.ColumnName;
import io.cdap.wrangler.api.parser.TokenType;
import io.cdap.wrangler.api.parser.UsageDefinition;
import io.cdap.wrangler.dq.ConvertString;

import java.util.List;

/**
 * A directive to convert a column's values to strings, optionally cleaning whitespace.
 */
@Plugin(type = Directive.TYPE)
@Name("convert-string")
@Description("Converts the specified column's values to strings and removes leading/trailing whitespace.")
public class ConvertStringDirective implements Directive {
    private String column;
    private ConvertString converter;

    @Override
    public UsageDefinition define() {
        UsageDefinition.Builder builder = UsageDefinition.builder("convert-string");
        builder.define("column", TokenType.COLUMN_NAME);
        return builder.build();
    }

    @Override
    public void initialize(Arguments args) {
        this.column = ((ColumnName) args.value("column")).value();
        this.converter = new ConvertString();
    }

    @Override
    public void destroy() {
        // no-op
    }

    @Override
    public List<Row> execute(List<Row> rows, ExecutorContext context) throws ErrorRowException {
        for (Row row : rows) {
            int idx = row.find(column);
            if (idx != -1) {
                Object value = row.getValue(idx);
                if (value != null) {
                    String stringValue = value.toString();
                    stringValue = converter.removeTrailingAndLeadingWhitespaces(stringValue);
                    row.setValue(idx, stringValue);
                }
            }
        }
        return rows;
    }
}
