package main

import (
	"github.com/hoisie/web"
	"net/http"
	"io/ioutil"
	"fmt"
	"os"
)

func hello(val string) string {
	return fetch()
}

func fetch() string {
	urls := []string{"http://www.163.com", "http://www.csdn.net"}
	var result string
	result = ""
	for _, url := range urls {
		resp, err := http.Get(url)
		if err != nil {
			fmt.Fprintf(os.Stderr, "fetch: %v\n", err)
			os.Exit(1)
		}
		b, err := ioutil.ReadAll(resp.Body)
		resp.Body.Close()
		if err != nil {
			fmt.Fprintf(os.Stderr, "fetch: reading %s: %v\n", url, err)
			os.Exit(1)
		}
		result += string(b)
		fmt.Printf("%s", b)
	}
	return result
}



func main() {
	web.Get("/(.*)", hello)
	web.Run("0.0.0.0:12862")
}