"""
author@liyi
date: 2019/5/6
desc: 爬取简易博客文字标题，链接，发布时间
"""

import requests
from bs4 import BeautifulSoup


class BlogCrawler(object):
    def __init__(self):
        self.root_url = "https://spidermen.cn/"
        self.root_html_tree = self.__get_doc_tree()

    def __get_doc_tree(self):
        res = requests.get(self.root_url)
        return BeautifulSoup(res.text, "html.parser")

    def get_publish_times(self):
        return self.root_html_tree.find_all("time", class_="entry-date published")

    def get_titles(self):
        return [i.a.text for i in self.root_html_tree.find_all("h2", class_="entry-title")]
        # for i in self.root_html_tree.find_all("h2", class_="entry-title"):
        #     print(i.a.text)

    def get_links(self):
        return [i.a["href"] for i in self.root_html_tree.find_all("h2", class_="entry-title")]


if __name__ == '__main__':
    handler = BlogCrawler()
    titles = handler.get_titles()
    links = handler.get_links()
    times = handler.get_publish_times()

    for i in range(len(titles)):
        print("文章标题:\t{}".format(titles[i]))
        print("文章链接:\t{}".format(links[i]))
        print("发布时间:\t{}".format(times[i]))
        print()
