/*
 * Copyright © 2015 Stefan Niederhauser (nidin@gmx.ch)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package guru.nidi.graphviz.engine;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.Map.Entry;

import static guru.nidi.graphviz.engine.Format.SVG;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FormatTest {
    static final String START1_7 =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"\n" +
                    " \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n" +
                    "<!-- Generated by graphviz version 2.40.1 (20161225.0304)\n" +
                    " -->\n" +
                    "<!-- Title: g Pages: 1 -->\n" +
                    "<svg";

    private static final String BEFORE = quote(
            " width='62pt' height='116pt' viewBox='0.00 0.00 62.00 116.00'>\n" +
                    "<g id='graph0' class='graph' transform='scale(1.3333 2.6666) rotate(0) translate(4 112)'>\n" +
                    "<text font-size='14.2'>a</text>\n" +
                    "</g></svg>");
    private static final String AFTER = quote(
            "<svg width='62px' height='116px' viewBox='0.00 0.00 62.00 116.00'>\n" +
                    "<g id='graph0' class='graph' transform='scale(1.0 2.0) rotate(0) translate(4 112)'>\n" +
                    "<text font-size='14.2'>a</text>\n" +
                    "</g></svg>");

    @ParameterizedTest
    @MethodSource
    void postProcess(Entry<String, String> values) {
        assertEquals(EngineResult.fromString(values.getValue()), SVG.postProcess(
                Graphviz.fromString("graph {dpi=96}"),
                EngineResult.fromString(START1_7 + values.getKey())));
    }

    static Set<Entry<String, String>> postProcess() {
        final Map<String, String> map = new HashMap<>();
        map.put(BEFORE, AFTER);
        map.put(BEFORE.replace("\n", "\r\n"), AFTER.replace("\n", "\r\n"));
        return map.entrySet();
    }

    private static String quote(String s) {
        return s.replace("'", "\"");
    }
}
