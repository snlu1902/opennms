/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2007-2012 The OpenNMS Group, Inc.
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

package org.opennms.netmgt.poller.remote.support;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.opennms.netmgt.poller.remote.PollerBackEnd;

public class ServerUnreachableAdaptorTest extends TestCase {
    public void testPollerStartingWhenBackEndReturnsFalse() {
        int id = 1;
        Map<String, String> details = new HashMap<String, String>();
        
        PollerBackEnd backEnd = createMock(PollerBackEnd.class);
        expect(backEnd.pollerStarting(id, details)).andReturn(false);
        
        ServerUnreachableAdaptor adaptor = new ServerUnreachableAdaptor();
        adaptor.setRemoteBackEnd(backEnd);
        
        replay(backEnd);
        adaptor.pollerStarting(id, details);
        verify(backEnd);
    }
}
