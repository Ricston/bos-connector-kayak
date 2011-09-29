/*
 * $Id: KayakConnector.groovy 1485 2011-09-27 16:49:51Z claude.mamo $
 * --------------------------------------------------------------------------------------
 * Copyright (c) Ricston Ltd.  All rights reserved.  http://www.ricston.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

import groovyx.net.http.RESTClient
import static groovyx.net.http.ContentType.*
import groovy.xml.DOMBuilder

def getSessionId(service, developerKey)
{
    sessionResponse = service.get( path : 'k/ident/apisession', 
                                 query : [token : developerKey] )
    
    return sessionResponse.data.sid
}

def getFlightSearchId(service, sessionId)
{

    searchResponse = service.get( path : 's/apisearch', 
                                  query : [ basicmode : 'true', oneway : oneway, origin : origin, destination : destination, destcode : destination, depart_date : departureDate, 
                                          depart_time : departureTime, return_date : returnDate, return_time : returnTime, travelers : travellers, cabin : cabin, action : 'doflights', 
                                          apimode : '1', _sid_ : sessionId ] )
    return searchResponse.data.searchid
}


def getHotelSearchId(service, sessionId)
{

    searchResponse = service.get( path : 's/apisearch', 
                                  query : [ basicmode : 'true', othercity : city, checkin_date : checkinDate, checkout_date : checkoutDate, guests1 : guests, rooms : rooms, 
                                            action : 'dohotels', apimode : '1', _sid_ : sessionId ] )                                                     
                                         
    return searchResponse.data.searchid
}

def getResults(service, searchType, searchId, sessionId)
{                             
    while ( ( resultResponse = querySearch(service, searchType, searchId, sessionId ) ).data.morepending == 'true')
    {
        sleep(searchPollingInterval)
    }
     
    resultResponse = querySearch(service, searchType, searchId, sessionId, resultResponse.data.count, TEXT)
    
    return resultResponse.data.text
}

def querySearch(service, searchType, searchId, sessionId, count = 0, contentType = XML)
{
    if ( searchType == 'f' )
        path = '/s/apibasic/flight'
    else
        path = '/s/apibasic/hotel'    

    service.get( path : path, 
                 query : [searchid : searchId, c : count, apimode : '1', _sid_ : sessionId],
                 contentType : contentType )
}

def convertToWellFormedXmlDoc(xmlDocument)
{
    StringBuffer wellFormedXmlDocument = new StringBuffer()
    xmlDocument = xmlDocument.readLines()
    dtdURL = getClass().getResource("/resources/xhtml1-transitional.dtd") 
    dtdPath = 'jar:' + dtdURL.getPath()
        
    xmlDocument.add(1, '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "' + dtdPath + '">')
    xmlDocument.each{ line ->    
        wellFormedXmlDocument.append(line)
    }
    
    return DOMBuilder.parse(new StringReader(wellFormedXmlDocument.toString()))        
}

def getService()
{
    kayak = new RESTClient( 'http://api.kayak.com' )
    kayak.contentType = XML

    return kayak
}

service = getService()
sid = getSessionId(service, developerKey)

if ( searchType == 'f' )
    searchId = getFlightSearchId(service, sid)
else
    searchId = getHotelSearchId(service, sid)

results = getResults(service, searchType, searchId, sid)

convertToWellFormedXmlDoc(results)
