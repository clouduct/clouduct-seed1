#!/usr/bin/env python
"""Creating a new project based on an existing one.
"""
import yaml

with open(".clouduct-reseed", "r") as file:
    data = yaml.load(file, yaml.Loader)
    print(data)