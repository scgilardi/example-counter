(ns keechma-counter.app
  (:require [keechma-counter.controllers.counter :as counter]
            [keechma-counter.ui.main :as main]
            [keechma-counter.subscriptions :as subscriptions]
            [keechma.app-state :as app-state]))

(enable-console-print!)

(def definition
  "Definition of the application.

  - `:controllers` param holds all of the controllers needed to run the app
  - Counter controller is registered under the `:counter` key. Main component
  has the `:counter` `:topic` assoc-ed to it which allows it to send the
  commands to the Counter controller.
  - `:component` param holds all the component needed to render the app
  - `:subscriptions` param holds the application subscriptions"
  {:controllers {:counter (counter/->Controller)}
   :components {:main (assoc main/component :topic :counter)}
   :subscriptions {:counter-value subscriptions/counter-value}
   :html-element (.getElementById js/document "app")})

(defonce running-app (clojure.core/atom nil))

(defn start!
  "Helper function that starts the application."
  []
  (reset! running-app (app-state/start! definition)))

(defn restart!
  "Helper function that restarts the application whenever the
  code is hot reloaded."
  []
  (let [current @running-app]
    (if current
      (app-state/stop! current start!)
      (start!))))

(restart!)

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )
