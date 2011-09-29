UPDATE: Kayak.com is no longer providing the API required for retrieving travel information from their site.
Consequently, our Kayak.com connectors are unable to collect available flight and hotel information. This 
project is only for illustration purposes.

At Ricston, we are developing a Bonita process which will be responsible for scheduling the business trips of 
our consultants. Such scheduling involves choosing and booking flights as well as hotels. To help us automate 
this internal process as much as possible, we have created two connectors to retrieve travel information from 
Kayak.com.

Kayak.com is a free online service, offering available flights and hotels at chosen locations. Our connectors 
get flights and hotels according to the user's criteria, and make them available to the Bonita process developer 
as an XML document which can be queried with XPath. 

Note: the Kayak.com connectors were tested with BOS-5.3.1.