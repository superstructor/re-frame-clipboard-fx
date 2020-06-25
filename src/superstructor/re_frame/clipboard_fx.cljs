(ns superstructor.re-frame.clipboard-fx
  (:require
    [oops.core :refer [oget oset! ocall oapply ocall! oapply!
                       oget+ oset!+ ocall+ oapply+ ocall!+ oapply!+]]
    [re-frame.core :refer [reg-fx dispatch]]))

;; Utilities
;; =============================================================================

(defn create-invisible-textarea
  [text]
  (doto (ocall js/document :createElement "textarea")
    (oset! :value text)
    (ocall :setAttribute "readonly" "")
    (oset! :style.position "absolute")
    (oset! :style.left "-9999px")))

;; Effects and Handlers
;; =============================================================================

(reg-fx
  :clipboard/copy
  (fn [{:keys [text on-success on-failure]
        :or   {on-success [:clipboard-no-on-success]
               on-failure [:clipboard-no-on-failure]}}]
    (try
      (let [el (create-invisible-textarea text)]
        (ocall js/document :body.appendChild el)
        (ocall el :select)
        (ocall js/document :execCommand "copy")
        (ocall js/document :body.removeChild el)
        (dispatch on-success))
      (catch js/Object error
        (dispatch (conj on-failure error))))))