#Stereotype

[![Build Status](https://travis-ci.org/josephwilk/stereotype.png?branch=master)](https://travis-ci.org/josephwilk/stereotype)

A library for setting up test data in Clojure.

Based on a simple idea:

>When you create test data in a test you should focus on the data that matters, push the rest out to defaults

Which provides greater clarity in your tests.

##Installation

Add the following dependency to your project.clj file:

https://clojars.org/stereotype

##Usage

```clojure
(:require 
  [stereotype.core :refer :all])
```

###Stereotypes

```clojure
(defstereotype :user {:username "josephwilk" :company "soundcloud"})

;Without inserting into the database
(stereotype :user) => {:username "josephwilk" :company "soundcloud"}

;Overide a default
(stereotype :user {:company "monkeys"}) => {:username "josephwilk" :company "monkeys"}

;Lazy evaluation
(defstereotype :user {:username "josephwilk"
                      :date_of_birth #(clj-time.core/now)})

;Lazy evaluation referencing attributes of itself.
;(useful when you need to know another lazy evaluated value)
(defstereotype :user {:username "josephwilk"
                      :date_of_birth #(clj-time.core/now)
                      :slug (fn [user] (str (:username user) (:date_of_birth user)))})
```

###Stereotypes with Korma

Korma: http://sqlkorma.com

```clojure
;Our Korma entities
(defentity user)
(defentity address)

;We can use the Korma entites as keys for stereotypes
(defstereotype address {:postcode 1234}

;We can cascade creation of stereotypes.
;Here creating a user will also create a address
(defstereotype user {:username "josephwilk" :address #(stereotype! address)}

;Inserts a user and address into the database
(stereotype! user) => {:id 2 :address_id 4 :username "josephwilk"}
```

###Sequences

Sequences are useful when you have a attribute in you stereotype (like email) that must be unique.

```clojure
(defsequence :email #(str "person" % "@example.com"))
; OR
(defsequence :email (fn [inc] (str "person" inc "@example.com")))

(defstereotype :user {:email #(generate :email)})

(stereotype :user) => {email "person1@example.com"}
(stereotype :user) => {email "person2@example.com"}

;Reset the counter to 1 for the email sequence
(reset-sequence! :email)

(stereotype :user) => {email "person1@example.com"}

;Reset all sequence counters to 1
(reset-all-sequences!)
```

###Transactional Stereotypes

Don't forget you can avoid having to cleanup the database by wrapping your tests in a transaction:

```clojure
(background (around :facts (transaction ?form (rollback))))

(facts "transactions are fun"
  (fact "it means we never have to clean the db"
    (stereotype! :user)
    (first (select users)) => {:username "josephwilk"}))
```

##Supports

Tested against:
* Clojure 1.3
* Clojure 1.4
* Clojure 1.5

##License
(The MIT License)

Copyright (c) 2013 Joseph Wilk

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the 'Software'), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
