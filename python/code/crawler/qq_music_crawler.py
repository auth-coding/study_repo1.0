"""
author@liyi
'date': '2019/5/6',
'desc': '爬取qq音乐指定歌手第一页歌曲列表',
"""
import requests
from urllib.parse import quote


class MusicCrawler(object):
    def __init__(self, **kwargs):
        self.api_url = "https://c.y.qq.com/soso/fcgi-bin/client_search_cp"
        self.params = {
            'ct': 24,
            'qqmusic_ver': 1298,
            'new_json': 1,
            'remoteplace': 'txt.yqq.song',
            'searchid': 62222136195267370,
            't': 0,
            'aggr': 1,
            'cr': 1,
            'catZhida': 1,
            'lossless': 0,
            'flag_qc': 0,
            'p': 1,
            'n': 10,
            'w': kwargs["singer"],
            'g_tk': 1785863683,
            'loginUin': 863022689,
            'hostUin': 0,
            'format': 'json',
            'inCharset': 'utf8',
            'outCharset': 'utf-8',
            'notice': 0,
            'platform': 'yqq.json',
            'needNewCode': 0,
        }
        # self.__url_encode()

    def __url_encode(self, encoding='utf-8'):
        """将中文参数值做urlencode"""
        self.params["w"] = quote(self.params["w"].encode(encoding))

    def get_musics(self):
        # requests默认会对参数做urlencode 所以不需要重复urlencode
        response = requests.get(self.api_url, params=self.params)
        json_data = response.json()
        return json_data["data"]["song"]["list"]


if __name__ == '__main__':
    # singer = input("please input the singer you want to crawl:\t")
    singer = "周杰伦"
    handler = MusicCrawler(singer=singer)
    musics = handler.get_musics()
    for music in musics:
        print("歌名:\t{}".format(music["title"]+music["subtitle"]))
        print("歌手:\t{}".format("+".join([singer["name"] for singer in music["singer"]])))
        print("链接:\t{}".format(music["url"]))
        print()
