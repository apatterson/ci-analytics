(ns ci-analytics.core
  (:require [compojure.handler :as handler])
  (:use [clojure.java.io :only [input-stream]]
        [compojure.core])
  (:import [com.google.api.client.http.javanet NetHttpTransport]
           [com.google.api.client.json.jackson JacksonFactory]
           [com.google.api.client.googleapis.auth.oauth2 GoogleClientSecrets
            GoogleAuthorizationCodeFlow$Builder]
           [com.google.api.client.auth.oauth2 Credential]
           [java.util Collections]
           [com.google.api.services.analytics AnalyticsScopes]
           [com.google.api.client.extensions.java6.auth.oauth2 AuthorizationCodeInstalledApp]))

(defroutes main-routes
  (GET "/analytics/callback" [code]
       {:status 200
        :body (str code)})
  (GET "/analytics" [& more]
       (let [http (NetHttpTransport.)
             json-factory (JacksonFactory.)
             client-secrets
             (GoogleClientSecrets/load json-factory
                                       (input-stream "src/ci_analytics/client_secrets.json"))
             flow (doto (GoogleAuthorizationCodeFlow$Builder.
                         http json-factory client-secrets
                         (Collections/singleton AnalyticsScopes/ANALYTICS_READONLY))
                    (.setAccessType "offline")
                    (.setApprovalPrompt "force"))
             credential (->
                          (.build flow)
                          (.loadCredential "anthony.patterson2.0@gmail.com"))
             redirect (->
                       (.build flow)
                       .newAuthorizationUrl
                       (.setRedirectUri "http://www.climateireland.ie/analytics/callback")
                       .build)
             p (print more)]
         (if (more :code) 
           {:status 200
            :body "ok"}
           {:status 303
            :headers {"Location" redirect}}))))

(def handler (handler/site main-routes))