import tornado.ioloop
import tornado.web
import json
import connect

class GetVoucherHandler(tornado.web.RequestHandler):

    # def post(self, *args, **kwargs):
    #     #解析传过来的数据：订单信息
    #     data = self.get_argument("data")
    #     data = json.loads(data)
    #     #往voucher表中插入报销凭证
    #     cur = connect.conn.cursor()
    #     #插入语句
    #     sql = 'INSERT INTO voucher (order_id,travelDate,travelTime,contactName,trainNumber,seatClass,seatNumber,startStation,destStation,price)VALUES(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)'
    #     try:
    #         cur.execute(sql,('1',"2017-10-18","10:58:00","lab401","D3301",1,"31","ShangHai","BeiJing",379))
    #         connect.conn.commit()
    #     finally:
    #         connect.conn.close
    #
    #     self.write(data)

    def get(self):
        #往voucher表中插入报销凭证
        cur = connect.conn.cursor()
        #插入语句
        sql = 'INSERT INTO voucher (order_id,travelDate,travelTime,contactName,trainNumber,seatClass,seatNumber,startStation,destStation,price)VALUES(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)'
        try:
            cur.execute(sql,('1',"2017-10-18","10:58:00","lab401","D3301",1,"31","ShangHai","BeiJing",379))
            connect.conn.commit()
        finally:
            connect.conn.close

        self.write("yes")

class TestHandler(tornado.web.RequestHandler):
    def get(self):
        self.write("testing")

class Test1Handler(tornado.web.RequestHandler):
    def get(self):
        self.write("testing ------ 2")

def make_app():
    return tornado.web.Application([
        (r"/getVoucher", GetVoucherHandler),
        (r"/test", TestHandler),
        (r"/test1", Test1Handler),
    ])

if __name__ == "__main__":
    app = make_app()
    app.listen(16101)
    tornado.ioloop.IOLoop.current().start()


    