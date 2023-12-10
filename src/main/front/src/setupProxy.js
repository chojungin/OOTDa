
//http-proxy-middleware 라이브러리를 통한 프록시 사용 
//npm install http-proxy-middleware --save

const {createProxyMiddleware} = require('http-proxy-middleware');

module.exports = app => {
    app.use('/api',
        createProxyMiddleware({
            target: 'http://localhost:8080',
            changeOrigin: true,
        })
    )
}