;
; Copyright 2016 Capital One Services, LLC
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
; See the License for the specific language governing permissions and limitations under the License.
;
; SPDX-Copyright: Copyright (c) Capital One Services, LLC
; SPDX-License-Identifier: Apache-2.0
;

(ns com.capitalone.commander.rest
  (:gen-class)
  (:require [meta-merge.core :refer [meta-merge]]
            [io.pedestal.log :as log]
            [com.capitalone.commander.rest.config :as config]
            [com.capitalone.commander.rest.system :as system]
            [com.capitalone.commander.util :as util]))

(set! *warn-on-reflection* true)

(def prod-config
  {:http {:env :prod}})

(def config
  (meta-merge config/defaults
              config/environ
              prod-config))

(defn -main
  [& args]
  (let [system (system/new-system config)]
    (log/info ::-main (str "Starting HTTP server on port" (-> system :http :port)))
    (log/debug ::config config)
    (util/run-system! system)))
