import random
import string

def generate_random_string(length):
    letters = string.ascii_lowercase
    return ''.join(random.choice(letters) for _ in range(length))

def generate_sample_file(filename, num_records):
    with open(filename, 'w') as file:
        # 写入表头
        file.write("field1,field2\n")

        # 生成 100 条记录
        for i in range(num_records):
            field1 = generate_random_string(5)
            field2 = generate_random_string(5)
            file.write(f"{field1},{field2}\n")

# 生成包含 100 条记录的 sample.txt 文件
generate_sample_file('sample.txt', 100)
