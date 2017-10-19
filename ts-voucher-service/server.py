import tornado.ioloop
import tornado.web
import json
import connect
import urllib3

class GetVoucherHandler(tornado.web.RequestHandler):

    def post(self, *args, **kwargs):
        #解析传过来的数据：订单id和车型指示（0代表普通，1代表动车高铁）
        data = self.get_argument("data")
        data = json.loads(data)
        orderId = data['orderId']
        type = data['type']
        #根据订单id查询是否存在对应的凭证
        queryVoucher = self.fetchVoucherByOrderId(self,orderId)
        if(queryVoucher == None):
            #根据订单id请求订单的详细信息
            orderResult = self.queryOrderByIdAndType(self,orderId,type)
            order = orderResult['order']

            #往voucher表中插入报销凭证
            cur = connect.conn.cursor()
            #插入语句
            sql = 'INSERT INTO voucher (order_id,travelDate,travelTime,contactName,trainNumber,seatClass,seatNumber,startStation,destStation,price)VALUES(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)'
            try:
                cur.execute(sql,(order['id'],order['travelDate'],order['travelTime'],order['contactsName'],order['trainNumber'],order['seatClass'],order['seatNumber'],order['from'],order['to'],order['price']))
                connect.conn.commit()
            finally:
                pass
            #再次查询，可以获得刚刚插入的凭证信息
            self.write(self.fetchVoucherByOrderId(self,orderId))
        else:
            self.write(queryVoucher)

    def queryOrderByIdAndType(self,orderId,type):
        #普通列车
        if(type == 0):
            url='http://ts-order-other-service:12032/order/getById'
        else:
            url='http://ts-order-service:12031/order/getById'
        values ={'orderId':orderId}
        jdata = json.dumps(values)             # 对数据进行JSON格式化编码
        req = urllib3.Request(url, jdata)      # 生成页面请求的完整数据
        response = urllib3.urlopen(req)        # 发送页面请求
        return json.loads(response)           # 获取服务器返回的页面信息

    def fetchVoucherByOrderId(self,orderId):
        #从voucher表中查询orderId对应的报销凭证
        cur = connect.conn.cursor()
        #查询语句
        sql = 'SELECT * FROM voucher where order_id = %s'
        try:
            cur.execute(sql,(orderId))
            voucher = cur.fetchone()
            connect.conn.commit()
            return voucher
        finally:
            pass

    # def get(self):
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
    #     self.write("yes")

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


    