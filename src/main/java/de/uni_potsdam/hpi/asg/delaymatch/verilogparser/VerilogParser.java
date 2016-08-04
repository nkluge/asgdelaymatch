package de.uni_potsdam.hpi.asg.delaymatch.verilogparser;

/*
 * Copyright (C) 2016 Norman Kluge
 * 
 * This file is part of ASGdelaymatch.
 * 
 * ASGdelaymatch is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ASGdelaymatch is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ASGdelaymatch.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.uni_potsdam.hpi.asg.common.io.FileHelper;
import de.uni_potsdam.hpi.asg.delaymatch.verilogparser.model.VerilogModule;

public class VerilogParser {
    private static final Logger  logger           = LogManager.getLogger();

    private static final Pattern modulepattern    = Pattern.compile("^\\s*module (.*) \\((.*)\\);\\s*$");
    private static final Pattern endmodulepattern = Pattern.compile("^\\s*endmodule\\s*$");
    private static final Pattern linepattern      = Pattern.compile("^.*;$");

    public VerilogModule parserVerilogStructure(File vfile) {
        List<String> lines = FileHelper.getInstance().readFile(vfile);
        if(lines == null) {
            return null;
        }
        Matcher m = null;
        Queue<String> linequeue = new LinkedList<>(lines);
        String line = null;

        Map<String, VerilogModuleContentParser> parserMap = new HashMap<>();
        VerilogModuleContentParser currContentParser = null;
        String rootModuleName = null;

        while((line = linequeue.poll()) != null) {
            do {
                m = linepattern.matcher(line);
                if(m.matches()) {
                    break;
                }
                // after endmodule ";" is not required (strangely)
                m = endmodulepattern.matcher(line);
                if(m.matches()) {
                    break;
                }
                if(line.equals("")) {
                    break;
                }

                String tmp = linequeue.poll();
                if(tmp == null) {
                    logger.error("no ; but null: #" + line + "#");
                    return null;
                }
                line = line + tmp;
            } while(true);

            m = modulepattern.matcher(line);
            if(m.matches()) {
                String modulename = m.group(1);
                String[] split = m.group(2).split(",");
                List<String> interfaceSignals = new ArrayList<>();
                for(String signal : split) {
                    interfaceSignals.add(signal.trim());
                }
                currContentParser = new VerilogModuleContentParser(interfaceSignals);
                parserMap.put(modulename, currContentParser);
                rootModuleName = modulename;
                continue;
            }

            m = endmodulepattern.matcher(line);
            if(m.matches()) {
                currContentParser = null;
            }

            if(currContentParser != null) {
                currContentParser.addLine(line);
            }
        }

        VerilogModule rootModule = null;

//        for(VerilogModule vm : modules) {
//            System.out.println(vm);
//        }

        return rootModule;
    }
}