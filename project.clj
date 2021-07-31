(defproject    superstructor/re-frame-clipboard-fx "lein-git-inject/version"
  :description "A re-frame effects handler for interacting with the system clipboard."
  :url         "https://github.com/superstructor/re-frame-clipboard-fx.git"
  :license     {:name "MIT"}

  :dependencies [[org.clojure/clojure       "1.10.3"   :scope "provided"]
                 [org.clojure/clojurescript "1.10.844" :scope "provided"
                  :exclusions [com.google.javascript/closure-compiler-unshaded
                               org.clojure/google-closure-library
                               org.clojure/google-closure-library-third-party]]
                 [thheller/shadow-cljs      "2.12.4"   :scope "provided"]
                 [binaryage/oops            "0.7.1" ]
                 [re-frame                  "1.2.0"    :scope "provided"]]

  :plugins      [[day8/lein-git-inject "0.0.14"]
                 [lein-shadow          "0.3.1"]
                 [lein-shell           "0.5.0"]]
  
  :middleware   [leiningen.git-inject/middleware]
  
  :profiles {:dev {:dependencies [[binaryage/devtools      "1.0.3"]]
                   :plugins      [[com.github.liquidz/antq "RELEASE"]
                                  [lein-pprint             "1.3.2"]]}}

  :jvm-opts ["-Xmx2g"]

  :source-paths   ["src"]
  :test-paths     ["test"]
  :resource-paths ["run/resources"]

  :shadow-cljs {:nrepl  {:port 8777}

                :builds {:browser-test
                         {:target    :browser-test
                          :ns-regexp "-test$"
                          :test-dir  "resources/public/js/test"}

                         :karma-test
                         {:target    :karma
                          :ns-regexp "-test$"
                          :output-to "target/karma-test.js"}}}

  :shell   {:commands {"karma" {:windows         ["cmd" "/c" "karma"]
                                :default-command "karma"}
                       "open"  {:windows         ["cmd" "/c" "start"]
                                :macosx          "open"
                                :linux           "xdg-open"}}}
  
  :aliases {"watch"  ["with-profile" "dev" "do"
                      ["clean"]
                      ["shadow" "watch" "browser-test"]]
            "ci" ["do"
                  ["clean"]
                  ["shadow" "compile" "karma-test"]
                  ["shell" "karma" "start" "--single-run" "--reporters" "junit,dots"]]}

  :deploy-repositories [["clojars" {:sign-releases false
                                    :url           "https://clojars.org/repo"
                                    :username      :env/CLOJARS_USERNAME
                                    :password      :env/CLOJARS_TOKEN}]]

  :release-tasks [["deploy" "clojars"]]

  :clean-targets [:target-path
                  "shadow-cljs.edn"
                  "resources/public/js/test"])





