;
; Copyright (c) 2018-present Walmart, Inc.
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

(ns com.walmartlabs.cond-let
  "Home for the cond-let macro.")

(defmacro cond-let
  "An alternative to `clojure.core/cond` where instead of a test/expression pair, it is possible
  to have a :let/binding vector pair."
  [& clauses]
  (cond (empty? clauses)
        nil

        (not (even? (count clauses)))
        (throw (ex-info (str `cond-let " requires an even number of forms")
                        {:form &form
                         :meta (meta &form)}))

        :else
        (let [[test expr-or-binding-form & more-clauses] clauses]
          (if (= :let test)
            `(let ~expr-or-binding-form (cond-let ~@more-clauses))
            ;; Standard case
            `(if ~test
               ~expr-or-binding-form
               (cond-let ~@more-clauses))))))
