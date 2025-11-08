const express = require('express');
const {
  createPost,
  getPosts,
  getPost,
  likePost,
  addComment,
  deletePost
} = require('../controllers/communityController');
const { protect } = require('../middleware/auth');
const { apiLimiter, createLimiter } = require('../middleware/rateLimiter');

const router = express.Router();

router.post('/posts', createLimiter, protect, createPost);
router.get('/posts', apiLimiter, protect, getPosts);
router.get('/posts/:id', apiLimiter, protect, getPost);
router.post('/posts/:id/like', apiLimiter, protect, likePost);
router.post('/posts/:id/comments', createLimiter, protect, addComment);
router.delete('/posts/:id', apiLimiter, protect, deletePost);

module.exports = router;
