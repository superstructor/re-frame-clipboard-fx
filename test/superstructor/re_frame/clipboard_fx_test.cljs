(ns superstructor.re-frame.clipboard-fx-test
  (:require
    [clojure.test :refer [deftest is testing async use-fixtures]]
    [re-frame.core :as rf]
    [superstructor.re-frame.clipboard-fx :as clipboard-fx]))

;; Utilities
;; =============================================================================

(deftest create-invisible-textarea
  (is (instance? js/HTMLTextAreaElement (clipboard-fx/create-invisible-textarea "content"))))

;; Effects and Handlers
;; =============================================================================

