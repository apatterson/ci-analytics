(ns ci-analytics.core
  (:use [clojure.java.io :only [input-stream]])
  (:import [com.google.api.client.http.javanet NetHttpTransport]
           [com.google.api.client.json.jackson JacksonFactory]
           [com.google.api.client.googleapis.auth.oauth2 GoogleClientSecrets
            GoogleAuthorizationCodeFlow$Builder]
           [com.google.api.client.auth.oauth2 Credential]
           [java.util Collections]
           [com.google.api.services.analytics AnalyticsScopes]
           [com.google.api.client.extensions.java6.auth.oauth2 AuthorizationCodeInstalledApp]
           [ com.google.api.client.extensions.jetty.auth.oauth2 LocalServerReceiver]))

(defn handler [req]
  (let [http (NetHttpTransport.)
        json-factory (JacksonFactory.)
        client-secrets
        (GoogleClientSecrets/load json-factory
                                  (input-stream "src/ci_analytics/client_secrets.json"))
        flow (doto (GoogleAuthorizationCodeFlow$Builder.
                    http json-factory client-secrets
                    (Collections/singleton AnalyticsScopes/ANALYTICS_READONLY))
               (.setAccessType "online")
               (.setApprovalPrompt "force"))
        app (doto (AuthorizationCodeInstalledApp. (.build flow) (LocalServerReceiver.))
                                                  (.authorize "user"))]
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body "Hello,"}))