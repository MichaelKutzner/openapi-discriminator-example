#!/usr/bin/env -S uv run --script
# /// script
# dependencies = [
#     "example-client @ file://${PROJECT_ROOT}/python-client",
# ]
# ///

from example.client import MyObject, MyEither, MyLeft, MyRight
from example.client import ApiClient, Configuration, DefaultApi

def main():
    # Simple objects with explicit discriminator
    left = MyLeft(left='Hello, World!', myValue=2.71, type='MyLeft')
    print(f"Left: '{left.to_json()}'")
    # Created from dict
    o = MyObject.from_dict({'@id': 5, 'feature': left.to_dict()})
    assert(o is not None)
    print(f"Object: '{o.to_json()}'")
    print()

    # Created by constructor, with default discriminator
    o2 = MyObject(id=123, mvValue='Object (2)', feature=MyEither(MyRight(right='Hello', mvValue='World')))
    print(f"Object(2): '{o2.to_json()}'")
    print()

    # Example request, object with explicit discriminator and default
    o3 = MyObject(id=17, feature=MyEither(MyRight(type='MyRight')))
    print(f"Object(3): '{o3.to_json()}'")
    resp = DefaultApi(ApiClient(Configuration(host='http://localhost:8000'))).echo_post(o3)
    print(f'Response: {resp.to_json()}')
    print()


if __name__ == '__main__':
    main()

