"""
author@liyi
date: 2019/5/5
desc: 爬取豆瓣电影top250的电影名字、url、评分、推荐语、排名序号
"""

import requests
from bs4 import BeautifulSoup
from urllib.parse import urljoin



def get_parsed_response(request_url):
    # 此函数只适用于get请求
    res = requests.get(request_url)
    return BeautifulSoup(res.text, "html.parser")

def main():
    offset = 0
    index = 1
    
    movies = []
    while offset < 250:
        # 每页25个item, 总共爬取250个
        url = "https://movie.douban.com/top250?start={}&filter=".format(offset)
        doc_tree = get_parsed_response(url)
        for item in doc_tree.find_all("div", class_="item"):
            movie = {}
            hd = item.find("div", class_="hd")
            movie["title"] = hd.find("span", class_="title").text
            movie["url"] = hd.a["href"]
            movie["index"] = index
            # 排列序号
            index += 1
            bd = item.find("div", class_="bd")
            movie["rating_num"] = bd.find("span", class_="rating_num").text
            inq = bd.find("span", class_="inq")
            if inq:
                movie["inq"] = inq.text 
            else:
                movie["inq"] = "无"
            movies.append(movie)
        # 翻页
        offset += 25
    for movie in movies[-5:]:
        print("第{}名".format(movie["index"]))
        print("电影名称: {}".format(movie["title"]))
        print("电影地址: {}".format(movie["url"]))
        print("电影评分: {}分".format(movie["rating_num"]))
        print("电影荐语: {}".format(movie["inq"]))
        print()


if __name__ == "__main__":
    main()
