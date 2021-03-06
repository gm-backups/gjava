﻿using System.Collections.Generic;
using System.Text;

namespace org.gcreator.Types
{
    public class Double : Number
    {
        private double i;

        public Double(double ii)
        {
            this.i = ii;
        }

        public override double getDouble()
        {
            return i;
        }

        public override String getString()
        {
            return new String("" + i);
        }

        public override string ToString()
        {
            return "" + i;
        }

        public override Object div(Object o)
        {
            return new Double(i / o.getDouble());
        }
    }
}
