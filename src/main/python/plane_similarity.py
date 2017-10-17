#!/bin/python
# -*- coding: utf-8 -*-

import math


class GridCoordinate(object):
    def __init__(self, x, y, z):
        assert isinstance(x, float
        assert isinstance(y, float)
        assert isinstance(z, float)
        self.x = x
        self.y = y
        self.z = z

    def to_polar(self, a, k0):
        lam = math.atan(math.sinh(self.x/k0*a))
        phi = math.asin(1)


class PolarCoordinate(object):
    def __init__(self, phi, lam, elv):
        self.phi = phi
        self.lam = lam
        self.elv = elv




def main():

    # init coordinate list

    # convert to polar

    # use calulaction method.


    return True
