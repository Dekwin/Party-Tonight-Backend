# Party-Tonight-Backend

SignUp
======

Maker
====

uri: /partymaker/maker/signup

method: POST

type: application/json

body: 
{

	"userName":"John",
	"phoneNumber":"066565",
	"email":"email.com",
	"emergencyContact":"contact",
	"password":123,
	"billing":{
		"card_number":"1234"
	}
}

Dancer
=======


uri: /partymaker/dancer/signup

method: POST

type: application/json

body: 
{

	"userName":"M",
	"phoneNumber":"066565",
	"email":"m.com",
	"emergencyContact":"contact",
	"password":123
}


SignIn:
======


uri: /signin

method: GET

type: application/json

header: Authorization = Basic bS5jb206MTIz


Create event
=============


uri: /partymaker/maker/event/create

method: POST

type: application/json

header: x-auth-token = signInMakerToken

body:

{

    "club_name": "NewEvent",
    "date": "cannot be null",
    "location": "cannot be null",
    "club_capacity": "cannot be null",
    "party_name": "event",
    "zip_code": "231",
    
    "bottles": [
      {
        "name": "champain",
        "price": "2",
        "type": "type",
        "available": "1000",
        "booked": "10"
      }
    ],
    "tickets": [
      {
        "price": "12",
        "available": "100",
        "booked": "2",
        "created_date": null
      }
    ],
    "tables": [
      {
        "price": "3",
        "type": "type",
        "available": "100",
        "booked": "10"
      }
    ],
    "photos": [
      {
        "photo": "photo"
      }
    ]
  }

  
Get event(for maker)
====================


uri: /partymaker/maker/event/get

method: GET

type: application/json

header: x-auth-token = signInMakerToken

response: array(list) of events


Get event(for dancer)
=====================


uri: /partymaker/dancer/event/get

method: GET

type: application/json

header: x-auth-token = signInDancerToken

header: zip_code = zipCodeEvent

response: array(list) of events


Revenue (tickets booked * ticket_price)
========

uri: /maker/event/revenue

method: GET

type: --//--

header: x-auth-token = signInMakerToken

header: party_name = party_name

response:

{
  "revenue": "516.0"
}

Bottles
=======

uri: /maker/event/bottles

method: GET

type: --//--

header: x-auth-token = signInMakerToken

header: party_name = party_name

response:

[
  {
    "id_bottle": 1,
    "name": "champain",
    "price": "2",
    "type": "type",
    "available": "1000",
    "booked": "10",
    "createdDate": null
  }
]

Tables
=======

uri: /maker/event/tables

method: GET

type: --//--

header: x-auth-token = signInMakerToken

header: party_name = party_name

response:

[
  {
    "id_table": 1,
    "price": "3",
    "type": "type",
    "available": "100",
    "booked": "10"
  }
]

Total
=======

uri: /maker/event/total

method: GET

type: --//--

header: x-auth-token = signInMakerToken

header: party_name = party_name

response:

{
  "withdrawn": "1200.0",
  "ticketsSales": "24.0",
  "bottleSales": "20.0",
  "tableSales": "30.0",
  "refunds": "-1126.0"
}

### Getting free for order tables

Getting list of free tables (6 phase, appendix 2.0):

uri: /dancer/event/{event_id}/tables/

method: GET

header: x-auth-token = signInToken

**Response**

```json
[
   {
     "type": "Bigger",
     "number": 1,
     "price": 100
   },
   {
     "type": "Bigger",
     "number": 2,
     "price": 100
   },
   {
     "type": "Bigger",
     "number": 3,
     "price": 100
   }
 ]
 ```
 
### Post a review about event
 
Save review in database.

uri: /dancer/event/review_post

method: POST

header: x-auth-token = signInToken

body: review

**Request**

```json
{
	"id_event" : 3,
	"content" : "Cool",
	"rating" : 3
}

```
 
 # Purchases
 
 ### Validate order
 To avoid conflicts and situations when customer wants to order 4 bottles, 
 but there are only 2 and so on. It cuts amount of ordered to maximum that 
 customer can buy. Or remove item from order (table for example, when it's number 
 already ordered by someone else).
 
 **uri**: /dancer/event/validate_booking
 
 **method**: POST
 
 **header**: x-auth-token = signInMakerToken
 
 **body**: array of bookings content as JSON
 
 **Request** 
 ```json
 [
     {
        "id_event": 4,
        "bottles": [{
            "amount": 4,
            "price": 22.0,
            "title": "Dd"
        }],
        "table": {
            "number": 6,
            "price": 22.0,
            "type": "22"
        },
        "ticket": {
            "price": 20.0,
            "type": ""
        }
     }, 
     {
        "id_event": 3,
        "bottles": [{
            "amount": 10,
            "price": 10.0,
            "title": "Small"
        }, {
            "amount": 5,
            "price": 25.0,
            "title": "Big"
        }],
        "table": {
            "number": 7,
            "price": 100.0,
            "type": "Bigger"
        }
     }
 ]
 ```
 
 **Response**
```json
 [
     {
        "id_event": 4,
        "bottles": [{
            "title": "Dd",
            "amount": 0,
            "price": 22.0
        }],
        "table": {
            "type": "22",
            "number": 6,
            "price": 22.0
        },
        "ticket": {
            "type": "",
            "price": 20.0
        }
     }, 
     {
        "id_event": 3,
        "bottles": [{
            "title": "Small",
            "amount": 4,
            "price": 10.0
        }, {
            "title": "Big",
            "amount": 5,
            "price": 25.0
        }],
        "table": null,
        "ticket": null
     }
 ]
````
 _recommendation_: use this method when cart is opened and while preparing to get invoices
  
  ### Get invoices
  
  Get array of invoices with necessary information for payment 
  in PayPal including transaction for fee (to owner). The most necessary stuff is
  billing_email (of merchant-promoter) and subtotal for single transaction.
  It is used MPL library by PayPal to send parallel payments. So data is formed according to it.
  
 **url**: dancer/event/get_invoices
 
 **method**: POST
 
 **header**: x-auth-token
 
 **body**: array of Booking.class 
 
 **request**
 ```json
 [
    {
        "bottles": [{
            "amount": 4,
            "price": 25.0,
            "title": "Big"
        }],
        "id_event": 3,
        "table": {
            "number": 7,
            "price": 100.0,
            "type": "Bigger"
        }
    }
 ]
  ```
  
  **response**
  
  ```json
  [
      {
        "id_transaction": 0,
        "completed": 0,
        "order": [],
        "billing_email": "promoter2@promoter.promoter",
        "subtotal": 190.0,
        "customer_email": "dancer@dancer.dancer",
        "seller_email": "promoter2@promoter.promoter",
        "id_event": 3
      }, {
        "id_transaction": 0,
        "completed": 0,
        "order": [],
        "billing_email": "owner_billing@owner.owner",
        "subtotal": 10.0,
        "customer_email": "dancer@dancer.dancer",
        "seller_email": "owner@owner.owner",
        "id_event": 0
      }
  ]
  ```
## Confirm invoices (book)

Make reservation of items in a system.
Need to be "referenced" right after successful payment for bookings.

##### _NOTE_: i have used headers as parameters. Sorry 

_NOTE_: can receive url-encoded request

**url**: dancer/event/confirm_invoices

**method**: POST

**headers**: 
* x-auth-token
* bookings[] - array of bookings that 
* transactions[] - array of payed transactions

**request**:

1. bookings[] =
```json
[
    {
        "bottles": [{
            "amount": 4,
            "price": 25.0,
            "title": "Big"
        }],
        "id_event": 3,
        "table": {
            "number": 7,
            "price": 100.0,
            "type": "Bigger"
        }
    }
] 
```
2. transactions[] = 
```json
[
    {
        "billing_email": "promoter2@promoter.promoter",
        "customer_email": "dancer@dancer.dancer",
        "id_event": 3,
        "id_transaction": 0,
        "seller_email": "promoter2@promoter.promoter",
        "subtotal": 190.0
    }, {
        "billing_email": "owner_billing@owner.owner",
        "customer_email": "dancer@dancer.dancer",
        "id_event": 0,
        "id_transaction": 0,
        "seller_email": "owner@owner.owner",
        "subtotal": 10.0
    }
]
```

response: 200 OK 
