"""
author@liyi
date: 2019/5/5
desc: python小课爬虫进阶练习 -- beautifulsoup
"""

import re

import requests
from bs4 import BeautifulSoup
from urllib.parse import urljoin

ROOT_URL = "http://books.toscrape.com/"


def get_classify_info():
    """
    爬取网上书店Books to Scrape中所有书的分类类型
    :return返回分类列表
    """
    res = requests.get(ROOT_URL)
    soup = BeautifulSoup(res.text, "html.parser")
    side_categories = soup.find("div", class_="side_categories")
    # 第一个a标签不是分类标签
    book_categories = side_categories.find_all("a")[1:]
    # for item in book_categories:
    #     print(item.text.strip())
    #     print(urljoin(ROOT_URL, item["href"]))
    #     print()
    return book_categories


def get_books(category_tag):
    """
    爬取某一分类下的图书列表,书名，评分，价格等三种信息
    :param category_tag:分类节点
    """
    category_url = urljoin(ROOT_URL, category_tag["href"])
    res = requests.get(category_url)
    soup = BeautifulSoup(res.text, "html.parser")
    names = soup.find_all("h3")
    prices = soup.find_all("p", class_="price_color")
    books = soup.find_all("article", class_="product_pod")
    print("共有{}本书\n".format(len(names)))
    for i in range(len(names)):
        print("书名:\t{}".format(names[i].a.text))
        print("价格:\t{}".format(re.findall("\d+.\d+", prices[i].text)[0]))
        grade = books[i].p["class"][-1]
        print("评分:\t{}".format(grade))
        print()


if __name__ == '__main__':
    tags = get_classify_info()
    get_books(tags[0])