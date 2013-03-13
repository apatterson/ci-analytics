(defproject ci-analytics "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :repositories [["google-api-services" "http://mavenrepo.google-api-java-client.googlecode.com/hg"]]
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [compojure "1.1.5"]
                 [com.google.apis/google-api-services-analytics "v3-rev9-1.7.0-beta"]
                 [com.google.http-client/google-http-client-jackson "1.13.1-beta"]
                 [com.google.http-client/google-http-client "1.13.1-beta"]
                 [com.google.api-client/google-api-client-java6 "1.13.1-beta"]
                 [com.google.apis/google-api-services-oauth2 "v2-rev9-1.7.2-beta"]
                 [com.google.oauth-client/google-oauth-client-java6 "1.13.1-beta"]
                 [com.google.oauth-client/google-oauth-client-jetty "1.13.1-beta"]]
  :ring {:handler ci-analytics.core/handler})
