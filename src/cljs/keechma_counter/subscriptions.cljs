(ns keechma-counter.subscriptions
  (:require [reagent.ratom :refer-macros [reaction]]))

(defn counter-value
  "Subscription that returns the current value of the counter."
  [app-state]
  (reaction
   (get-in @app-state [:kv :counter])))
