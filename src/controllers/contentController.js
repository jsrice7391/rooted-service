const DailyContent = require('../models/DailyContent');
const UserProgress = require('../models/UserProgress');
const User = require('../models/User');

// @desc    Get today's content
// @route   GET /api/content/today
// @access  Private
exports.getTodayContent = async (req, res) => {
  try {
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    
    const tomorrow = new Date(today);
    tomorrow.setDate(tomorrow.getDate() + 1);

    const content = await DailyContent.findOne({
      publishDate: { $gte: today, $lt: tomorrow },
      isPublished: true
    });

    if (!content) {
      return res.status(404).json({
        success: false,
        message: 'No content available for today'
      });
    }

    // Check if user has completed this content
    const progress = await UserProgress.findOne({
      user: req.user.id,
      dailyContent: content._id
    });

    res.status(200).json({
      success: true,
      data: {
        content,
        completed: !!progress,
        progress
      }
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: error.message
    });
  }
};

// @desc    Get all content
// @route   GET /api/content
// @access  Private
exports.getAllContent = async (req, res) => {
  try {
    const { page = 1, limit = 10, contentType, difficulty, tags } = req.query;

    const query = { isPublished: true };
    
    if (contentType) query.contentType = contentType;
    if (difficulty) query.difficulty = difficulty;
    if (tags) query.tags = { $in: tags.split(',') };

    const content = await DailyContent.find(query)
      .sort({ publishDate: -1 })
      .limit(limit * 1)
      .skip((page - 1) * limit);

    const count = await DailyContent.countDocuments(query);

    res.status(200).json({
      success: true,
      count,
      totalPages: Math.ceil(count / limit),
      currentPage: page,
      data: content
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: error.message
    });
  }
};

// @desc    Get content by ID
// @route   GET /api/content/:id
// @access  Private
exports.getContent = async (req, res) => {
  try {
    const content = await DailyContent.findById(req.params.id);

    if (!content) {
      return res.status(404).json({
        success: false,
        message: 'Content not found'
      });
    }

    res.status(200).json({
      success: true,
      data: content
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: error.message
    });
  }
};

// @desc    Mark content as completed
// @route   POST /api/content/:id/complete
// @access  Private
exports.completeContent = async (req, res) => {
  try {
    const { timeSpent, notes, reflection, rating } = req.body;

    const content = await DailyContent.findById(req.params.id);
    if (!content) {
      return res.status(404).json({
        success: false,
        message: 'Content not found'
      });
    }

    // Check if already completed
    let progress = await UserProgress.findOne({
      user: req.user.id,
      dailyContent: req.params.id
    });

    if (progress) {
      // Update existing progress
      progress.timeSpent = timeSpent || progress.timeSpent;
      progress.notes = notes || progress.notes;
      progress.reflection = reflection || progress.reflection;
      progress.rating = rating || progress.rating;
      await progress.save();
    } else {
      // Create new progress
      progress = await UserProgress.create({
        user: req.user.id,
        dailyContent: req.params.id,
        timeSpent,
        notes,
        reflection,
        rating
      });

      // Update user's daily streak and check-in
      await updateUserStreak(req.user.id);
    }

    res.status(200).json({
      success: true,
      data: progress
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: error.message
    });
  }
};

// @desc    Get user's progress history
// @route   GET /api/content/progress/history
// @access  Private
exports.getProgressHistory = async (req, res) => {
  try {
    const { page = 1, limit = 20 } = req.query;

    const progress = await UserProgress.find({ user: req.user.id })
      .populate('dailyContent', 'title contentType publishDate')
      .sort({ completedAt: -1 })
      .limit(limit * 1)
      .skip((page - 1) * limit);

    const count = await UserProgress.countDocuments({ user: req.user.id });

    res.status(200).json({
      success: true,
      count,
      totalPages: Math.ceil(count / limit),
      currentPage: page,
      data: progress
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: error.message
    });
  }
};

// Helper function to update user's daily streak
async function updateUserStreak(userId) {
  const user = await User.findById(userId);
  const today = new Date();
  today.setHours(0, 0, 0, 0);
  
  const lastCheckIn = user.lastCheckIn ? new Date(user.lastCheckIn) : null;
  
  if (lastCheckIn) {
    lastCheckIn.setHours(0, 0, 0, 0);
    const daysDiff = Math.floor((today - lastCheckIn) / (1000 * 60 * 60 * 24));
    
    if (daysDiff === 1) {
      // Consecutive day
      user.dailyStreak += 1;
    } else if (daysDiff > 1) {
      // Streak broken
      user.dailyStreak = 1;
    }
    // If daysDiff === 0, same day, don't change streak
  } else {
    // First check-in
    user.dailyStreak = 1;
  }
  
  user.lastCheckIn = new Date();
  user.totalCheckIns += 1;
  await user.save();
}
