package de.uni_potsdam.hpi.asg.delaymatch.check.values.model;

/*
 * Copyright (C) 2017 Norman Kluge
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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import de.uni_potsdam.hpi.asg.delaymatch.model.DelayMatchModuleInst;

@XmlAccessorType(XmlAccessType.NONE)
public class ValuesXmlEach {

    public static final String      NOEACHID = Integer.toString(-1);

    //@formatter:off
    @XmlAttribute(name = "id", required = true)
    private String id;
    
    @XmlElement(name = "instance")
    private List<ValuesXmlInstance> instances;
    @XmlElement(name = "minValueFactor")
    private Float minValueFactor;
    @XmlElement(name = "maxValueFactor")
    private Float maxValueFactor;
    //@formatter:on

    protected ValuesXmlEach() {
    }

    public ValuesXmlEach(String id) {
        this.id = id;
        this.instances = new ArrayList<>();
    }

    public void setMaxValueFactor(Float maxValueFactor) {
        this.maxValueFactor = maxValueFactor;
    }

    public void setMinValueFactor(Float minValueFactor) {
        this.minValueFactor = minValueFactor;
    }

    public void addInstance(ValuesXmlInstance instance) {
        instances.add(instance);
    }

    public String getId() {
        return id;
    }

    public List<ValuesXmlInstance> getInstances() {
        return instances;
    }

    public Float getMaxValueFactor() {
        return maxValueFactor;
    }

    public Float getMinValueFactor() {
        return minValueFactor;
    }

    public ValuesXmlInstance getInstance(DelayMatchModuleInst inst) {
        for(ValuesXmlInstance valinst : instances) {
            if(valinst.getName().equals(inst.getInstName())) {
                return valinst;
            }
        }
        return null;
    }
}
