## Usage

Clone, and cd into the `rente` folder.

```
lein figwheel
```

Wait for figwheel to finish and notify browser of changed files! Then in another terminal tab:

```
lein run
```

point browser to:
http://localhost:8080

Open up your browser console, which will log the data returned from the server when you click either of the 2 buttons for socket with/without callback (pull/push).


## Rente Demo

Rente can be seen running here:

https://enterlab-rente.herokuapp.com/

(the application goes idle after a while, and may take around 30 seconds to wake up)

## Deploy to Heroku

To make Rente run on Heroku, you need to let Leiningen on Heroku use the "package" build task.

To do this, and point Leiningen on Heroku to the "package" target, add the following config variable to Heroku by running this command:

```
heroku config:add LEIN_BUILD_TASK=package
```

Everything is nicely wrapped in shiny purple foil if you simply click this button:

[![Deploy](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

Enjoy!


## Noobs

Newbies can start here.

Install Leiningen - it may seem hard, but only takes a minute (at least on a real OS, perhaps up to 5 minutes on Windows):
http://leiningen.org/

If you don't know Clojure, start here:
http://clojure.org

Other entry level resources:

https://aphyr.com/posts/301-clojure-from-the-ground-up-welcome

http://www.tryclj.com/

http://www.clojurebook.com/


## Comments & Suggestions

Please don't hesitate to contact us if you have any questions/suggestions etc.!

## Mobile App?

If you're interested in seeing how to use this setup to build a "native"-ish mobile app for iOS and Android built with Cordova, take a look at Enterlab CorDeviCLJS here: https://github.com/enterlab/cordevicljs.

## Special Thanks goes to


*Dan Holmsand* (https://github.com/holmsand) and contributors for Reagent (https://github.com/reagent-project/reagent)

*Peter Taoussanis* (https://github.com/ptaoussanis) for Sente (https://github.com/ptaoussanis/sente)

Inspired by https://github.com/gsnewmark/gsn-spa-template.git

## License

Copyright © Enterlab 2014-2017

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
