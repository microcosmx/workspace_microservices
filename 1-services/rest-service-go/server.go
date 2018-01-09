package main

import (
	"github.com/hoisie/web"
)

func hello(val string) string {
	return "test go: " + val
}

func main() {
	web.Get("/(.*)", hello)
	web.Run("0.0.0.0:16102")
}
