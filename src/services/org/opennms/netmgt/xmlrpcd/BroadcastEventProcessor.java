//
// Copyright (C) 2003 Sortova Consulting Group, Inc.  All rights reserved.
// Parts Copyright (C) 1999-2001 Oculan Corp.  All rights reserved.
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
//
// For more information contact:
//      OpenNMS Licensing       <license@opennms.org>
//      http://www.opennms.org/
//      http://www.sortova.com/
//
//
// Tab Size = 8
//
package org.opennms.netmgt.xmlrpcd;

import java.lang.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;

import org.apache.log4j.Category;
import org.opennms.core.utils.ThreadCategory;

import org.opennms.core.queue.FifoQueue;
import org.opennms.core.queue.FifoQueueException;

import org.opennms.netmgt.eventd.EventListener;
import org.opennms.netmgt.eventd.EventIpcManagerFactory;
import org.opennms.netmgt.config.XmlrpcdConfigFactory;

// castor generated classes
import org.opennms.netmgt.xml.event.Event;
import org.opennms.netmgt.config.xmlrpcd.SubscribedEvent;

/**
 *
 * @author <a href="mailto:jamesz@blast.com">James Zuo</a>
 * @author <a href="http://www.opennms.org/">OpenNMS</a>
 */
final class BroadcastEventProcessor
	implements EventListener
{

        /**
         * The location where incoming events of interest
         * are enqueued
         */
        private FifoQueue       m_eventQ;

        /**
         * The maximam size of the event queue.
         */
        private int             m_maxQSize;
        
	/**
	 * Create message selector to set to the subscription
	 */
	BroadcastEventProcessor(FifoQueue eventQ, int maxQSize, Enumeration eventEnum)
	{
		// Create the selector for the ueis this service is interested in
		//
		List ueiList = new ArrayList();

                while(eventEnum.hasMoreElements())
                {
                        SubscribedEvent sEvent = (SubscribedEvent)eventEnum.nextElement();
                        ueiList.add(sEvent.getUei());
                }

                m_eventQ = eventQ;
                m_maxQSize = maxQSize;
		EventIpcManagerFactory.init();
		EventIpcManagerFactory.getInstance().getManager().addEventListener(this, ueiList);
	}

	/**
	 * Unsubscribe from eventd
	 */
	public void close()
	{
		EventIpcManagerFactory.getInstance().getManager().removeEventListener(this);
	}

	/**
	 * This method is invoked by the EventIpcManager
	 * when a new event is available for processing.
	 * Each message is examined for its Universal Event Identifier
	 * and the appropriate action is taking based on each UEI.
	 *
	 * @param event	The event 
	 *
	 */
	public void onEvent(Event event)
	{
		Category log = ThreadCategory.getInstance(getClass());

		String eventUei = event.getUei();
		if (eventUei == null)
			return;
			
		if (log.isDebugEnabled())
			log.debug("Received event: " + eventUei);

                try
                {
                        if (XmlrpcdConfigFactory.getInstance().eventSubscribed(eventUei))
                        {
                                if (m_eventQ.size() >= m_maxQSize)
                                {
                                        m_eventQ.remove(1000);
                                        
                                        if(log.isDebugEnabled())
                                                log.debug("Event " + eventUei + " removed from event queue");
                                }

                                m_eventQ.add(event);

                                if(log.isDebugEnabled())
                                        log.debug("Event " + eventUei + " added to event queue");
                        }
                }
                catch(InterruptedException ex)
                {
                        log.error("Failed to process event", ex);
                        return;
                }
                catch(FifoQueueException ex)
                {
                        log.error("Failed to process event", ex);
                        return;
                }
                catch(Throwable t)
                {
                        log.error("Failed to process event", t);
                        return;
                }
	}
	
	/**
	 * Return an id for this event listener
	 */
	public String getName()
	{
		return "Xmlrpcd:BroadcastEventProcessor";
	}
}
