package main

import (
	"github.com/hoisie/web"
)

func hello(val string) string {
	str := "[News][Title] Welcome to Our Train Ticket System. [Content] Hello World. This service is for news"
	return str
}

func main() {
	web.Get("/(.*)", hello)
	web.Run("0.0.0.0:12862")
}