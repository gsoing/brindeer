FROM kong:2.0.2-alpine

USER root

ENV KONG_PLUGINS=bundled,strip-prefix,security-headers,oidc

# Add libs
ADD lib/resty/openidc.lua /usr/local/openresty/lualib/resty/openidc.lua

# Add oidc plugin
ADD plugins/oidc /usr/local/share/lua/5.1/kong/plugins/oidc


# Install dependencies
RUN luarocks install /tmp/lua-resty-session-2.8-1.all.rock && \
    luarocks install /tmp/lua-resty-jwt-0.1.11-0.src.rock && \
    luarocks install /tmp/lua-resty-http-0.11-0.src.rock && \
    luarocks install /tmp/lua-resty-hmac-v1.0-1.all.rock && \
    luarocks install /tmp/lua-resty-openidc-1.4.0-1.src.rock && \
    luarocks install /tmp/kong-oidc-1.0.4-0.src.rock

USER kong