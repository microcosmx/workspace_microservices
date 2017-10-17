package main

import (
	"github.com/hoisie/web"
)

func main() {
	web.Get("/(.*)", sayHello("Test"))
	web.Run("0.0.0.0:12862")
}