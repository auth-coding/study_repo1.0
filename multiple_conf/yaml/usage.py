import yaml

def main(filename):
    if filename.endswith(".yaml"):
        with open(filename) as f:
            return yaml.load("".join(f.readlines()))

if __name__ == "__main__":
    file_name = "dimission.yaml"
    conf = main(file_name)
    conf["人员信息"][0].update(conf["人员信息"][1])
    conf["人员信息"] = conf["人员信息"][0]
    print(conf)
