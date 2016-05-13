(ns super-skunk.core
  (:gen-class)
  (:require [clj-http.client :as client]
            [cheshire.core :as json]))

(defn request [opts url]
  (let [response (client/get url opts)]
   (when (= 200 (:status response))
    (json/parse-string (:body response) true))))

(defn url
  [page-number]
  (str "http://www.leafly.com/explore/page-" page-number))

(defn page
  [page-number]
  (request {:with-credentials? false :headers {:accept "application/json"}} (url page-number)))

(defn strains
  [page-index is-last-page all-strains]
  ; (println (str "Fetching Page: " page-index))
  (if is-last-page
    all-strains
    (if-let [response-body (page page-index)]
     (let [paging-context (get-in response-body [:Model :PagingContext])
           is-last-page (:IsLastPage paging-context)
           page-idx (:PageIndex paging-context)]
          (Thread/sleep 1000)
          (strains (inc page-idx) is-last-page (concat all-strains (get-in response-body [:Model :Strains]))))
     all-strains)))


(defn -main
  [& args]
  (println "Super Skunk")
  (let [all-strains (strains 0 false [])
        json-str-strains (json/generate-string {:strains all-strains} {:pretty true})]
   (println (str "Total Strains Fetched: " (count all-strains)))
   (println (str "Writing strains.json"))
   (with-open [out-file (clojure.java.io/writer "./output/strains.json" :encoding "UTF-8")]
    (.write out-file json-str-strains))))
