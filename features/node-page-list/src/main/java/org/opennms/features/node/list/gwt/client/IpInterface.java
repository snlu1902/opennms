/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2011-2012 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2012 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.features.node.list.gwt.client;

import com.google.gwt.core.client.JavaScriptObject;


public class IpInterface extends JavaScriptObject {

    protected IpInterface() {}
    
    public final native String getId()/*-{
        return '' + this.id;
    }-*/;
    
    public final native String getIpAddress() /*-{
        return this.ipAddress;
    }-*/;
    
    public final native String getIpHostName() /*-{
        return this.hostName;
    }-*/;
    
    public final native String getManaged() /*-{
        return this.isManaged;
    }-*/;
    
    public final native String isDown() /*-{
        return this.isDown === null? null : ('' + this.isDown);
    }-*/;
    
    public final native String getMonitoredServiceCount()/*-{
        return '0';
    }-*/;

    public final native String getIfIndex() /*-{
        return this.ifIndex === null? null : ('' + this.ifIndex);
    }-*/;
}
