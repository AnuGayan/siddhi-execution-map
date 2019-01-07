/*
 * Copyright (c)  2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.extension.siddhi.execution.map;

import org.wso2.siddhi.annotation.Example;
import org.wso2.siddhi.annotation.Extension;
import org.wso2.siddhi.annotation.Parameter;
import org.wso2.siddhi.annotation.ReturnAttribute;
import org.wso2.siddhi.annotation.util.DataType;
import org.wso2.siddhi.core.config.SiddhiAppContext;
import org.wso2.siddhi.core.exception.SiddhiAppRuntimeException;
import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.core.executor.function.FunctionExecutor;
import org.wso2.siddhi.core.util.config.ConfigReader;
import org.wso2.siddhi.query.api.definition.Attribute;
import org.wso2.siddhi.query.api.exception.SiddhiAppValidationException;

import java.util.Map;

/**
 * get(HashMap , Key , Type)
 * Returns required attribute value'.
 * Accept Type(s): (HashMap , ValidKey)
 * Return Type(s): Object
 */
@Extension(
        name = "get",
        namespace = "map",
        description = "This returns the value object, that corresponds to the given key, from the map. ",
        parameters = {
                @Parameter(name = "map",
                        description = "The map from where the value should be obtained",
                        type = DataType.OBJECT,
                        optional = false
                ),
                @Parameter(
                        name = "key",
                        description = "The key of the value which needs to be returned",
                        type = DataType.OBJECT,
                        optional = false
                )
        },
        returnAttributes = @ReturnAttribute(
                description = "This returns the value object from the map that corresponds to the given key.",
                type = DataType.OBJECT),

        examples = @Example(
                syntax = "get(company,1)",
                description = "This function returns the value that is associated with the key, i.e., 1, from a " +
                        "map named company.")
)

public class GetFunctionExtension extends FunctionExecutor {
    private Attribute.Type returnType = Attribute.Type.OBJECT;

    @Override
    protected void init(ExpressionExecutor[] attributeExpressionExecutors,
                        ConfigReader configReader,
                        SiddhiAppContext siddhiAppContext) {
        if (attributeExpressionExecutors.length != 2) {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to map:get() function, " +
                    "required 2, but found " + attributeExpressionExecutors.length);
        }
    }

    @Override
    protected Object execute(Object[] data) {
        Map map;
        if (data[0] instanceof Map) {
            map = (Map) data[0];
        } else {
            throw new SiddhiAppRuntimeException("First attribute value must be of type java.util.Map");
        }
        return map.get(data[1]);
    }

    @Override
    protected Object execute(Object data) {
        return null;
    }

    @Override
    public Attribute.Type getReturnType() {
        return returnType;
    }

    @Override
    public Map<String, Object> currentState() {
        return null;    //No need to maintain a state.
    }

    @Override
    public void restoreState(Map<String, Object> state) {
        //Since there's no need to maintain a state, nothing needs to be done here.
    }
}


