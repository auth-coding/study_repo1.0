"""
author@liyi
date: 2019/5/7
desc:通过快递公司名称和快递单号查询快递信息
"""
import requests
from xpinyin import Pinyin


class Query(object):
    def __init__(self, type_, postid):
        self.type_ = self.__get_pinyin_type(type_)
        print(self.type_)
        self.post_id = postid
        self.url = "https://www.kuaidi100.com/query"
        self.temp = 0.12345678

    def __get_pinyin_type(self, type_):
        """将汉子转为拼音并返回"""
        pin = Pinyin()
        return pin.get_pinyin(type_).replace("-", "")

    def query(self):
        params = {
            "type": self.type_,
            "postid": self.post_id,
            "temp": self.temp,
            "phone": ""
        }
        headers = {
            "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4)"
                         " AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36",
            "Host": "www.kuaidi100.com",
            "Referer": "https://www.kuaidi100.com/"
        }
        response = requests.get(url=self.url, params=params, headers=headers)
        print(response.url)
        return response.json()


if __name__ == '__main__':
    handler = Query("汇通快递", "71330102164040")
    res = handler.query()
    print(res)
    for col in res["data"]:
        print(col)