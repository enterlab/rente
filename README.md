## Usage

Clone, and cd into the `rente` folder.

```
lein figwheel
```

Wait for figwheel to finish and notify browser of changed files! Then in another terminal tab:

```
lein figwheel
```

point browser to:
http://localhost:8080

## Deploy to Heroku

To make Rente run on Heroku, you need to let Leiningen on Heroku use the "package" build task.

To make this work you need to point Heroku to this build pack:
https://github.com/heroku/heroku-buildpack-clojure

To do this, and point Leiningen on Heroku to the "package" target, add the following two config variables to Heroku by running this command:

```
heroku config:add BUILDPACK_URL=https://github.com/heroku/heroku-buildpack-clojure LEIN_BUILD_TASK=package
```

Deploy to Heroku as usual, and enjoy.

## Comments & Suggestions

Please don't hesitate to contact us if you have any questions/suggestions etc.!

## Special Thanks goes to


*Dan Holmsand* (https://github.com/holmsand) and contributors for Reagent (https://github.com/reagent-project/reagent)

*Peter Taoussanis* (https://github.com/ptaoussanis) for Sente (https://github.com/ptaoussanis/sente)

Inspired by https://github.com/gsnewmark/gsn-spa-template.git

## License

Copyright © Enterlab 2014-2015

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
