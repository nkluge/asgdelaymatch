package de.uni_potsdam.hpi.asg.delaymatch.trace.helper;

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

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class TraceSimulationStepFactory extends BasePooledObjectFactory<TraceSimulationStep> {

    @Override
    public TraceSimulationStep create() throws Exception {
        return new TraceSimulationStep();
    }

    @Override
    public PooledObject<TraceSimulationStep> wrap(TraceSimulationStep obj) {
        return new DefaultPooledObject<TraceSimulationStep>(obj);
    }

    @Override
    public void passivateObject(PooledObject<TraceSimulationStep> p) throws Exception {
        p.getObject().getStates().clear();
        p.getObject().getSequence().clear();
//        p.getObject().getNextSteps().clear();
//        p.getObject().setPrevStep(null);
        p.getObject().setNextState(null);
        p.getObject().setStart(null);
    }

}
