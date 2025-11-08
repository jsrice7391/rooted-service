const User = require('../models/User');

// @desc    Get users near location (for community building)
// @route   GET /api/users/nearby
// @access  Private
exports.getNearbyUsers = async (req, res) => {
  try {
    const { latitude, longitude, maxDistance = 50000 } = req.query; // maxDistance in meters (default 50km)

    if (!latitude || !longitude) {
      return res.status(400).json({
        success: false,
        message: 'Please provide latitude and longitude'
      });
    }

    // Validate and sanitize input
    const lat = parseFloat(latitude);
    const lng = parseFloat(longitude);
    const maxDist = parseInt(maxDistance, 10);

    if (isNaN(lat) || isNaN(lng) || lat < -90 || lat > 90 || lng < -180 || lng > 180) {
      return res.status(400).json({
        success: false,
        message: 'Invalid latitude or longitude values'
      });
    }

    if (isNaN(maxDist) || maxDist < 0 || maxDist > 500000) {
      return res.status(400).json({
        success: false,
        message: 'Invalid maxDistance value (must be between 0 and 500000 meters)'
      });
    }

    const users = await User.find({
      _id: { $ne: req.user.id },
      'location.coordinates': {
        $near: {
          $geometry: {
            type: 'Point',
            coordinates: [lng, lat]
          },
          $maxDistance: maxDist
        }
      }
    }).select('username firstName lastName location.city location.state faithJourney.beliefLevel');

    res.status(200).json({
      success: true,
      count: users.length,
      data: users
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: error.message
    });
  }
};

// @desc    Get user profile
// @route   GET /api/users/:id
// @access  Private
exports.getUserProfile = async (req, res) => {
  try {
    const user = await User.findById(req.params.id)
      .select('-password')
      .populate('connections', 'username firstName lastName');

    if (!user) {
      return res.status(404).json({
        success: false,
        message: 'User not found'
      });
    }

    res.status(200).json({
      success: true,
      data: user
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: error.message
    });
  }
};

// @desc    Add connection
// @route   POST /api/users/connect/:id
// @access  Private
exports.addConnection = async (req, res) => {
  try {
    const targetUserId = req.params.id;

    if (targetUserId === req.user.id) {
      return res.status(400).json({
        success: false,
        message: 'Cannot connect with yourself'
      });
    }

    const targetUser = await User.findById(targetUserId);
    if (!targetUser) {
      return res.status(404).json({
        success: false,
        message: 'User not found'
      });
    }

    // Add connection for both users
    await User.findByIdAndUpdate(req.user.id, {
      $addToSet: { connections: targetUserId }
    });

    await User.findByIdAndUpdate(targetUserId, {
      $addToSet: { connections: req.user.id }
    });

    res.status(200).json({
      success: true,
      message: 'Connection added successfully'
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: error.message
    });
  }
};

// @desc    Remove connection
// @route   DELETE /api/users/connect/:id
// @access  Private
exports.removeConnection = async (req, res) => {
  try {
    const targetUserId = req.params.id;

    await User.findByIdAndUpdate(req.user.id, {
      $pull: { connections: targetUserId }
    });

    await User.findByIdAndUpdate(targetUserId, {
      $pull: { connections: req.user.id }
    });

    res.status(200).json({
      success: true,
      message: 'Connection removed successfully'
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: error.message
    });
  }
};

// @desc    Get user connections
// @route   GET /api/users/connections
// @access  Private
exports.getConnections = async (req, res) => {
  try {
    const user = await User.findById(req.user.id)
      .populate('connections', 'username firstName lastName location.city faithJourney.beliefLevel dailyStreak');

    res.status(200).json({
      success: true,
      count: user.connections.length,
      data: user.connections
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: error.message
    });
  }
};
