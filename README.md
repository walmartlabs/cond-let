# com.walmartlabs/cond-let

A micro-library around the useful `cond-let` macro.

`cond-let` acts like a `cond`, but adds
`:let` terms that are followed by a binding form (like `let`).

This allows conditional code to introduce new local symbols; the result
is clearer, more linear code, that doesn't make a march for the
right margin.


## Usage

An example from the Clockwork library:

```clj
(defn ^:private has-necessary-capabilities?
  "Does the worker have all the capabilities that the job needs?"
  [state worker-id task]
  (cond-let

    :let [job-id (:job-id task)]

    (nil? job-id)
    true

    :let [capabilities (get-in state [:jobs job-id :clockwork/required-capabilities])]

    (empty? capabilities)
    true

    :let [worker-capabilities (get-in state [:workers worker-id :capabilities])]

    (empty? worker-capabilities)
    false

    :else
    ;; It's ok for the worker to have *more* capabilities than are specified.
    ;; For each required capability, we need an exact match.
    (= (select-keys worker-capabilities (keys capabilities))
       capabilities)))
```

## License

Copyright © 2018 Walmart

Distributed under the Apache Software License 2.0.
