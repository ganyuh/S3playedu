export const checkToken = token => {
    let res = null;
    try {
        if (token) {
            let tokenInfo = token.split(".");
            if (tokenInfo.length === 3) {
                res = JSON.parse(decode64(tokenInfo[1]));
                // console.log("Payload: ", res)
                if (res && res.exp) {
                    res.expired = res?.exp < new Date().getTime() / 1000;
                }
            }
        }
    } catch (e) {
    }
    return res;
};

export const encode64 = text => {
    return btoa(String.fromCharCode(...new TextEncoder().encode(text)));
};

export const decode64 = text => {
    text = text.replace(/-|_/g, e => {
        var map = {
            "-": "+",
            _: "/",
        };
        return map[e];
    });
    return new TextDecoder().decode(Uint8Array.from(atob(text), c => c.charCodeAt(0)));
};

export const base64ToBytes = base64 => {
    const binString = atob(base64);
    return Uint8Array.from(binString, m => m.codePointAt(0));
};

export const bytesToBase64 = bytes => {
    const binString = Array.from(bytes, x => String.fromCodePoint(x)).join("");
    return btoa(binString);
};

/**
 // Usage
bytesToBase64(new TextEncoder().encode("a Ā 𐀀 文 🦄")); // "YSDEgCDwkICAIOaWhyDwn6aE"
new TextDecoder().decode(base64ToBytes("YSDEgCDwkICAIOaWhyDwn6aE")); // "a Ā 𐀀 文 🦄"
 */
