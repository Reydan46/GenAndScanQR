/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Rurio Luca
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package ParseQR.vcard;

/**
 * Created by luca on 7/26/16.
 */
class VCardCostant {


    public static final String KEY_BEGIN_VCARD = "BEGIN:VCARD";
    public static final String KEY_END_VCARD = "END:VCARD";
    public static final String KEY_SEPARATOR = "\r?\n";
    public static final String KEY_SPLIT = ":";
    public static final String KEY_LINE_ESCAPE = "\n";
    public static final String KEY_VERSION="VERSION:3.0";
    public static final String KEY_NAME = "N";
    public static final String KEY_FORMATTEDNAME = "FN";

    public static final String KEY_COMPANY = "ORG";
    public static final String KEY_TITLE = "TITLE";
    public static final String KEY_PHONE = "TEL";
    public static final String KEY_WEB = "URL";
    public static final String KEY_EMAIL = "EMAIL";
    public static final String KEY_ADDRESS = "ADR";
    public static final String KEY_NOTE = "NOTE";
}
