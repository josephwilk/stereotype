#Stereotype

[![Build Status](https://travis-ci.org/josephwilk/stereotype-clj.png?branch=master)](https://travis-ci.org/josephwilk/stereotype-clj)

A library for setting up test data in Clojure.

Based on a simple idea:

*When you create test data in a test you should focus on the data that matters, push the rest out to defaults*

Which provides greater clarity in your tests.

##Installation

Add the following dependency to your project.clj file:

https://clojars.org/stereotype-clj

##Usage

```clojure
(defsterotype :user {:username "josephwilk" :company "soundcloud"})

;without inserting into the database
(sterotype :user) => {:username "josephwilk" :company "soundcloud"}

;Overide a default
(sterotype :user {:company "monkeys"}) => {:username "josephwilk" :company "monkeys"}

;inserts user into the database
(sterotype! :user)
```

Don't forget you can avoid having to cleanup the database by wrapping your tests in a transaction:

```clojure
(background (around :facts (transaction ?form (rollback))))

(facts "transactions are fun"
  (fact "it means we never have to clean the db"
    (sterotype! :user)
    (first (select users)) => {:username "josephwilk"}))
```

##License
(The MIT License)

Copyright (c) 2013 Joseph Wilk

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the 'Software'), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.