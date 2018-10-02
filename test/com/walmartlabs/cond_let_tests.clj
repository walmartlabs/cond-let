;
; Copyright (c) 2018-present, Walmart Inc
;
; Licensed under the Apache License, Version 2.0 (the "License")
; you may not use this file except in compliance with the License.
; You may obtain a copy of the License at
;
;     http://www.apache.org/licenses/LICENSE-2.0
;
; Unless required by applicable law or agreed to in writing, software
; distributed under the License is distributed on an "AS IS" BASIS,
; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
; See the License for the specific language governing permissions and
; limitations under the License.
;
;

(ns com.walmartlabs.cond-let-tests
  (:require
    [com.walmartlabs.cond-let :refer [cond-let]]
    [clojure.test :refer [deftest is]])
  (:import (clojure.lang ExceptionInfo)))


(deftest basic-case
  (is (= 1
         (cond-let
           false
           nil

           :let [x 1
                 y false]

           y
           :y

           :else
           x))))

(deftest empty-is-nil
  (is (nil? (cond-let))))

(deftest checks-number-of-forms
  (is (thrown-with-msg? ExceptionInfo #"requires an even number of forms"
                        (eval `(cond-let false
                                         nil

                                         :let [x 1]

                                         ;; Note: missing an :else or something here
                                         x)))))
